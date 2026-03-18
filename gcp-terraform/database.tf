# Cloud SQL MySQL 인스턴스 생성
resource "google_sql_database_instance" "mysql_instance" {
  name             = "${local.common_project_name}-database"
  database_version = "MYSQL_8_0"
  region           = local.region

  settings {
    tier      = "db-f1-micro"
    disk_size = 100

    ip_configuration {
      ipv4_enabled    = true

      authorized_networks {
        name  = "all"
        value = "0.0.0.0/0"
      }
    }

    backup_configuration {
      enabled                        = true
      binary_log_enabled             = true # MySQL PITR(특정 시점 복구)을 위해 필수
      point_in_time_recovery_enabled = true
    }

    availability_type = "REGIONAL"
  }

  deletion_protection = false
}

# 데이터베이스 생성
resource "google_sql_database" "mysql_db" {
  name     = local.database_name
  instance = google_sql_database_instance.mysql_instance.name
}

# 데이터베이스 사용자 생성
resource "google_sql_user" "mysql_user" {
  name     = "root" # MySQL 기본 관리자 이름
  instance = google_sql_database_instance.mysql_instance.name
  password = var.db_password # 비밀번호 변수 처리! (여기에 직접 적지 마세요)
}