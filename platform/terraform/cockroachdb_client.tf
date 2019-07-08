# cockroachdb cluster vars
variable "cockroachdb_client_role" {
  default = "cockroachdb-client"
}
resource "digitalocean_tag" "cockroachdb_client_role" {
  name = "${var.cockroachdb_client_role}"
}

# cockroachdb droplets and ansible inventory
resource "digitalocean_droplet" "cockroachdb_client_droplet" {
  image = "${var.droplet_image}"
  name = "${var.cockroachdb_client_role}"
  region = "${var.primary_datacenter_name}"
  size = "${var.droplet_size}"
  private_networking = true
  ssh_keys = ["${var.ssh_fingerprint}"]
  tags = ["${digitalocean_tag.target_env.name}","${var.cockroachdb_client_role}","${digitalocean_tag.consul_client_role.name}"]
}

resource "ansible_host" "cockroachdb_client_droplet" {
  inventory_hostname = "${digitalocean_droplet.cockroachdb_client_droplet.name}"
  groups = ["${var.target_env}","${var.consul_client_role}","${var.cockroachdb_client_role}"]
  vars {
    ansible_host = "${digitalocean_droplet.cockroachdb_client_droplet.ipv4_address}"
    ansible_python_interpreter = "${var.ansible_python_interpreter}"
    datacenter_name = "${var.primary_datacenter_name}"
    datacenter_role = "${var.primary_datacenter_role}"
  }
}

