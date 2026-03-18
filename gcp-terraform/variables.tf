variable "db_password" {
  description = "MySQL Root 비밀번호"
  type        = string
  sensitive   = true  # 화면에 비밀번호가 노출되는 것을 막아줍니다.
}