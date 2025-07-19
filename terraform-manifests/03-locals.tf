# Local Values Block
locals {
  # Shorten the names for more readability
  rg_name      = "${var.business_unit}-${var.environment}-${var.resoure_group_name}"
  cluster_name = "${var.business_unit}-${var.environment}"
  search-name  = "${var.resoure_group_name}-${var.environment}"
  # Common tags to be assigned to all resources
  service_name = "AI Services"
  owner        = "Agile Solutions"
  common_tags = {
    Service = local.service_name
    Owner   = local.owner
  }
}