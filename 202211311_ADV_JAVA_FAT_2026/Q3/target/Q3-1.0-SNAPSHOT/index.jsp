<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Client Registration</title>
</head>
<body>

<h2>Add Client</h2>

<form id="clientForm">
    Name: <input type="text" name="name" required><br><br>
    Email: <input type="email" name="email" required><br><br>
    Phone: <input type="text" name="phone" required><br><br>
    <button type="submit">Save</button>
</form>

<hr>

<h2>Client List</h2>
<table border="1">
    <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Phone</th>
        </tr>
    </thead>
    <tbody id="clientTable"></tbody>
</table>

<script>
document.getElementById("clientForm").addEventListener("submit", function (e) {
    e.preventDefault();

    let name = document.querySelector("input[name='name']").value;
    let email = document.querySelector("input[name='email']").value;
    let phone = document.querySelector("input[name='phone']").value;

    fetch("addClient", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: "name=" + encodeURIComponent(name) +
              "&email=" + encodeURIComponent(email) +
              "&phone=" + encodeURIComponent(phone)
    })
    .then(response => response.json())
    .then(data => {
        alert("Client Saved!");
        loadClients();
        document.getElementById("clientForm").reset();
    });
});

function loadClients() {
    fetch("getClients")
        .then(response => response.json())
        .then(data => {

            console.log("Clients:", data); // DEBUG

            let table = document.getElementById("clientTable");
            table.innerHTML = "";

            if (data.length === 0) {
                table.innerHTML = "<tr><td colspan='4'>No Records Found</td></tr>";
                return;
            }

            data.forEach(client => {

                let row = document.createElement("tr");

                row.innerHTML =
                    "<td>" + client.id + "</td>" +
                    "<td>" + client.name + "</td>" +
                    "<td>" + client.email + "</td>" +
                    "<td>" + client.phone + "</td>";

                table.appendChild(row);
            });
        })
        .catch(error => console.error("Load error:", error));
}

window.addEventListener("DOMContentLoaded", loadClients);
</script>


</body>
</html>
