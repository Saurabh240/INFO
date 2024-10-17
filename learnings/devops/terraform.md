### Terraform Basics to Advanced with Code Examples

Terraform is an open-source Infrastructure as Code (IaC) tool that allows you to define, provision, and manage infrastructure using a high-level configuration language.

---

### 1. **Basic Concepts in Terraform**

- **Providers:** These are responsible for managing the lifecycle of the infrastructure (e.g., AWS, Azure, GCP).
- **Resources:** The components you want to create, like VMs, networks, etc.
- **State:** A snapshot of your infrastructure that Terraform uses to keep track of the resources it manages.
- **Modules:** A collection of related resources grouped together.
- **Variables:** Input values to parameterize your configuration.
- **Outputs:** Export data from one module for use in another.
- **Workspaces:** Isolated Terraform states for different environments (e.g., `dev`, `prod`).

---

### 2. **Getting Started with Terraform**

1. **Installation:**
   - Download Terraform from the official [Terraform website](https://www.terraform.io/downloads.html).
   - Initialize your project with `terraform init`.

2. **Basic Example: Creating an AWS EC2 Instance**

```hcl
provider "aws" {
  region = "us-west-2"
}

resource "aws_instance" "example" {
  ami           = "ami-0c55b159cbfafe1f0" # AMI ID for Amazon Linux 2
  instance_type = "t2.micro"

  tags = {
    Name = "TerraformExampleInstance"
  }
}

output "instance_id" {
  value = aws_instance.example.id
}
```

Steps:
- `terraform init`: Initializes the project.
- `terraform plan`: Previews changes Terraform will make.
- `terraform apply`: Applies changes and creates infrastructure.

---

### 3. **Intermediate Concepts**

1. **Variables and Outputs:**

```hcl
variable "instance_type" {
  type        = string
  description = "The type of instance to use"
  default     = "t2.micro"
}

resource "aws_instance" "example" {
  ami           = "ami-0c55b159cbfafe1f0"
  instance_type = var.instance_type
}

output "public_ip" {
  value = aws_instance.example.public_ip
}
```

2. **Using Terraform Modules:**
   - Modules allow you to reuse infrastructure across projects. Example usage:

```hcl
module "network" {
  source = "./modules/network"
  vpc_cidr = "10.0.0.0/16"
}
```

3. **Data Sources:**
   - Data sources allow you to fetch information from the provider.

```hcl
data "aws_ami" "latest" {
  most_recent = true
  owners      = ["amazon"]

  filter {
    name   = "name"
    values = ["amzn2-ami-hvm-*-x86_64-gp2"]
  }
}
```

---

### 4. **Advanced Concepts**

1. **Remote State Management:**
   - By default, Terraform stores state locally. You can configure remote backends like S3 to store the state remotely and ensure team collaboration.

```hcl
terraform {
  backend "s3" {
    bucket = "my-terraform-state-bucket"
    key    = "global/s3/terraform.tfstate"
    region = "us-west-2"
  }
}
```

2. **Workspaces:**
   - Workspaces allow you to manage multiple environments in the same configuration.

```bash
terraform workspace new dev
terraform workspace new prod
```

3. **Terraform with CI/CD:**
   - You can integrate Terraform into CI/CD pipelines for automation. For example, using GitHub Actions to run Terraform:
   
```yaml
name: Terraform

on:
  push:
    branches:
      - main

jobs:
  terraform:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout code
        uses: actions/checkout@v2

      - name: Set up Terraform
        uses: hashicorp/setup-terraform@v1

      - name: Terraform Init and Apply
        run: |
          terraform init
          terraform apply -auto-approve
```

---

### 5. **AKS Cluster with Terraform**

To create an Azure Kubernetes Service (AKS) cluster using Terraform, follow the steps below.

1. **AKS Terraform Configuration:**

```hcl
provider "azurerm" {
  features {}
}

resource "azurerm_resource_group" "aks" {
  name     = "aks-resource-group"
  location = "East US"
}

resource "azurerm_kubernetes_cluster" "aks_cluster" {
  name                = "my-aks-cluster"
  location            = azurerm_resource_group.aks.location
  resource_group_name = azurerm_resource_group.aks.name
  dns_prefix          = "aksdns"

  default_node_pool {
    name       = "agentpool"
    node_count = 2
    vm_size    = "Standard_DS2_v2"
  }

  identity {
    type = "SystemAssigned"
  }

  network_profile {
    network_plugin    = "azure"
    network_policy    = "calico"
    load_balancer_sku = "standard"
  }
}

resource "azurerm_kubernetes_cluster_node_pool" "additional_node_pool" {
  name                  = "additionalpool"
  kubernetes_cluster_id = azurerm_kubernetes_cluster.aks_cluster.id
  vm_size               = "Standard_DS2_v2"
  node_count            = 1
}

output "kube_config" {
  value = azurerm_kubernetes_cluster.aks_cluster.kube_config_raw
  sensitive = true
}
```

2. **Steps to Apply:**

- **Step 1**: Initialize Terraform
   ```bash
   terraform init
   ```

- **Step 2**: Plan your changes
   ```bash
   terraform plan
   ```

- **Step 3**: Apply the configuration
   ```bash
   terraform apply
   ```

3. **Access AKS Cluster:**
   After the cluster is created, you can connect to it using `kubectl` by configuring the kubeconfig.

```bash
az aks get-credentials --resource-group aks-resource-group --name my-aks-cluster
kubectl get nodes
```

---

### 6. **Best Practices**

- **Use Version Control for Infrastructure Code**: Store your Terraform code in a version control system like Git.
- **Modularize Configurations**: Break down infrastructure into reusable modules.
- **Remote State with State Locking**: Always use remote state with locking to prevent concurrent changes.
- **Plan Before Applying**: Always run `terraform plan` before `terraform apply` to review changes.

---

### Terraform vs Ansible (Both Infrastructure as code)

Terraform: 
Mainly infrastructure provisioning tool
relatively new
more advanced in orchestration
better for provisioning the infrastructure

Ansible:
Mainly a configuration tool
more mature
better for configuring that infrastructure


terraform command for different stages

refresh: query infrastructure provider to get current state

plan: create an execution plan

apply: execute the plan

destroy: destroy the resources/infrastructure

