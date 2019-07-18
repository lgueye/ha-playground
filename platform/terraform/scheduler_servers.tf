# scheduler cluster vars
variable "scheduler_server_role" {
  default = "scheduler-server"
}

resource "digitalocean_tag" "scheduler_server_role" {
  name = "${var.scheduler_server_role}"
}

resource "digitalocean_droplet" "scheduler_server_01_droplet" {
  image = "${var.droplet_image}"
  name = "${var.scheduler_server_role}-01"
  region = "${var.primary_datacenter_name}"
  size = "${var.droplet_size}"
  private_networking = true
  ssh_keys = ["${var.ssh_fingerprint}"]
  tags = ["${digitalocean_tag.target_env.name}","${digitalocean_tag.discovery_client_role.name}","${digitalocean_tag.scheduler_server_role.name}"]
}

resource "ansible_host" "scheduler_server_01_droplet" {
    inventory_hostname = "${digitalocean_droplet.scheduler_server_01_droplet.name}"
    groups = ["${var.target_env}","${var.discovery_client_role}","${var.scheduler_server_role}","${var.primary_datacenter_role}"]
    vars {
      ansible_host = "${digitalocean_droplet.scheduler_server_01_droplet.ipv4_address}"
      ansible_python_interpreter = "${var.ansible_python_interpreter}"
      datacenter_name = "${var.primary_datacenter_name}"
      datacenter_role = "${var.primary_datacenter_role}"
    }
}
resource "digitalocean_droplet" "scheduler_server_02_droplet" {
  image = "${var.droplet_image}"
  name = "${var.scheduler_server_role}-02"
  region = "${var.fallback_datacenter_name}"
  size = "${var.droplet_size}"
  private_networking = true
  ssh_keys = ["${var.ssh_fingerprint}"]
  tags = ["${digitalocean_tag.target_env.name}","${digitalocean_tag.discovery_client_role.name}","${digitalocean_tag.scheduler_server_role.name}"]
}

resource "ansible_host" "scheduler_server_02_droplet" {
    inventory_hostname = "${digitalocean_droplet.scheduler_server_02_droplet.name}"
    groups = ["${var.target_env}","${var.discovery_client_role}","${var.scheduler_server_role}","${var.fallback_datacenter_role}"]
    vars {
      ansible_host = "${digitalocean_droplet.scheduler_server_02_droplet.ipv4_address}"
      ansible_python_interpreter = "${var.ansible_python_interpreter}"
      datacenter_name = "${var.fallback_datacenter_name}"
      datacenter_role = "${var.fallback_datacenter_role}"
    }
}

