# store clients vars
variable "store_client_role" {
  default = "store-client"
}
resource "digitalocean_tag" "store_client_role" {
  name = "${var.store_client_role}"
}

# store droplets and ansible inventory
resource "digitalocean_droplet" "store_client_droplet" {
  image = "${var.droplet_image}"
  name = "${var.store_client_role}"
  region = "${var.primary_datacenter_name}"
  size = "${var.droplet_size}"
  private_networking = true
  ssh_keys = ["${var.ssh_fingerprint}"]
  tags = ["${digitalocean_tag.target_env.name}","${var.store_client_role}","${digitalocean_tag.discovery_client_role.name}"]
}

resource "ansible_host" "store_client_droplet" {
  inventory_hostname = "${digitalocean_droplet.store_client_droplet.name}"
  groups = ["${var.target_env}","${var.discovery_client_role}","${var.store_client_role}"]
  vars {
    ansible_host = "${digitalocean_droplet.store_client_droplet.ipv4_address}"
    ansible_python_interpreter = "${var.ansible_python_interpreter}"
    datacenter_name = "${var.primary_datacenter_name}"
    datacenter_role = "${var.primary_datacenter_role}"
  }
}

