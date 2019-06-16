# rabbitmq cluster vars
variable "rabbitmq_server_role" {
  default = "rabbitmq-server"
}

resource "digitalocean_tag" "rabbitmq_server_role" {
  name = "${var.rabbitmq_server_role}"
}

resource "digitalocean_droplet" "rabbitmq_server_01_droplet" {
  image = "${var.droplet_image}"
  name = "${var.rabbitmq_server_role}-01"
  region = "${var.primary_datacenter_name}"
  size = "${var.droplet_size}"
  private_networking = true
  ssh_keys = ["${var.ssh_fingerprint}"]
  tags = ["${digitalocean_tag.target_env.name}","${digitalocean_tag.consul_client_role.name}","${digitalocean_tag.rabbitmq_server_role.name}"]
}

resource "ansible_host" "rabbitmq_server_01_droplet" {
    inventory_hostname = "${digitalocean_droplet.rabbitmq_server_01_droplet.name}"
    groups = ["${var.target_env}","${var.consul_client_role}","${var.rabbitmq_server_role}","${var.primary_datacenter_role}"]
    vars {
      ansible_host = "${digitalocean_droplet.rabbitmq_server_01_droplet.ipv4_address}"
      ansible_python_interpreter = "${var.ansible_python_interpreter}"
      datacenter_name = "${var.primary_datacenter_name}"
      datacenter_role = "${var.primary_datacenter_role}"
    }
}
resource "digitalocean_droplet" "rabbitmq_server_02_droplet" {
  image = "${var.droplet_image}"
  name = "${var.rabbitmq_server_role}-02"
  region = "${var.fallback_datacenter_name}"
  size = "${var.droplet_size}"
  private_networking = true
  ssh_keys = ["${var.ssh_fingerprint}"]
  tags = ["${digitalocean_tag.target_env.name}","${digitalocean_tag.consul_client_role.name}","${digitalocean_tag.rabbitmq_server_role.name}"]
}

resource "ansible_host" "rabbitmq_server_02_droplet" {
    inventory_hostname = "${digitalocean_droplet.rabbitmq_server_02_droplet.name}"
    groups = ["${var.target_env}","${var.consul_client_role}","${var.rabbitmq_server_role}","${var.fallback_datacenter_role}"]
    vars {
      ansible_host = "${digitalocean_droplet.rabbitmq_server_02_droplet.ipv4_address}"
      ansible_python_interpreter = "${var.ansible_python_interpreter}"
      datacenter_name = "${var.fallback_datacenter_name}"
      datacenter_role = "${var.fallback_datacenter_role}"
    }
}

resource "digitalocean_droplet" "rabbitmq_server_03_droplet" {
  image = "${var.droplet_image}"
  name = "${var.rabbitmq_server_role}-03"
  region = "${var.ternary_datacenter_name}"
  size = "${var.droplet_size}"
  private_networking = true
  ssh_keys = ["${var.ssh_fingerprint}"]
  tags = ["${digitalocean_tag.target_env.name}","${digitalocean_tag.consul_client_role.name}","${digitalocean_tag.rabbitmq_server_role.name}"]
}

resource "ansible_host" "rabbitmq_server_03_droplet" {
    inventory_hostname = "${digitalocean_droplet.rabbitmq_server_03_droplet.name}"
    groups = ["${var.target_env}","${var.consul_client_role}","${var.rabbitmq_server_role}","${var.ternary_datacenter_role}"]
    vars {
      ansible_host = "${digitalocean_droplet.rabbitmq_server_03_droplet.ipv4_address}"
      ansible_python_interpreter = "${var.ansible_python_interpreter}"
      datacenter_role = "${var.ternary_datacenter_role}"
      datacenter_name = "${var.ternary_datacenter_name}"
    }
}
