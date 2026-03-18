#resource "google_compute_network" "vpc_network" {
#  name = "my-vpc"
#}
#
## 퍼블릭 서브넷 (asia-northeast3-a)
#resource "google_compute_subnetwork" "public_subnet_a" {
#  name          = "${local.common_project_name}-subnet-public-a"
#  network       = google_compute_network.vpc_network.name
#  ip_cidr_range = local.public_a_cidr_block
#  region        = local.region
#  private_ip_google_access = true
#}
#
## 퍼블릭 서브넷 (asia-northeast3-c)
#resource "google_compute_subnetwork" "public_subnet_c" {
#  name          = "${local.common_project_name}-subnet-public-c"
#  network       = google_compute_network.vpc_network.name
#  ip_cidr_range = local.public_c_cidr_block
#  region        = local.region
#  private_ip_google_access = true
#}