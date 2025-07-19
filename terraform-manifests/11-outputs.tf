# Output Values - Resource Group
output "resource_group_id" {
  description = "Resource Group ID"
  # Atrribute Reference
  value = azurerm_resource_group.aks_rg.id
}
output "resource_group_name" {
  description = "Resource Group name"
  # Argument Reference
  value = azurerm_resource_group.aks_rg.name
}

output "instrumentation_key" {
  description = "The Instrumentation Key for this Application Insights component"
  value = azurerm_application_insights.example.instrumentation_key
  sensitive = false
}

output "app_id" {
  description = "The App ID associated with this Application Insights component"
  value = azurerm_application_insights.example.app_id
  sensitive = false
}