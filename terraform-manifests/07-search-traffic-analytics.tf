resource "azurerm_log_analytics_workspace" "insights" {
  name                = "${var.environment}-logs-${random_pet.aksrandom.id}"
  location            = azurerm_resource_group.aks_rg.location
  resource_group_name = azurerm_resource_group.aks_rg.name
  retention_in_days   = 30
}

resource "azurerm_application_insights" "example" {
  name                = "${var.environment}-appinsights-${random_pet.aksrandom.id}"
  location            = azurerm_resource_group.example.location
  resource_group_name = azurerm_resource_group.aks_rg.location
  workspace_id        = azurerm_log_analytics_workspace.insights.id
  application_type    = "web"
}