resource "azurerm_kubernetes_cluster" "aks" {
  name                = local.cluster_name
  location            = var.resoure_group_location
  resource_group_name = local.rg_name
  kubernetes_version  = data.azurerm_kubernetes_service_versions.current.latest_version
  dns_prefix          = "ai"
  default_node_pool {
    name       = "default"
    node_count = 2
    vm_size    = "Standard_DS2_v2"
  }
  # Identity (System Assigned or Service Principal)
  identity { type = "SystemAssigned" }


  azure_active_directory_role_based_access_control {
    admin_group_object_ids = [azuread_group.aks_administrators.id]
  }

  # Network Profile
  network_profile {
    load_balancer_sku = "standard"
    network_plugin    = "azure"
  }

  # Linux Profile
  linux_profile {
    admin_username = "ubuntu"
    ssh_key {
      key_data = file(data.external.ssh_key_generator.result.public_key)
    }
  }




}