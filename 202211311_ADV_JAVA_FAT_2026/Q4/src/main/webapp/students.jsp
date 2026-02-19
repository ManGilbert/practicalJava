<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Students REST Management</title>

        <style>
            .modal {
                display: none;
                position: fixed;
                top: 20%;
                left: 35%;
                background: white;
                padding: 20px;
                border: 1px solid black;
            }
        </style>
    </head>
    <body>

        <h2>Students Management</h2>

        <input type="number" id="searchId" placeholder="Enter ID">
        <button onclick="getStudentById()">Search</button>

        <br><br>

        <button onclick="openModal()">Add Student</button>

        <br><br>

        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Marks</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody id="studentTable"></tbody>
        </table>


        <div id="studentModal" class="modal">

            <h3 id="modalTitle">Add Student</h3>

            <input type="hidden" id="studentId">

            Name:<br>
            <input type="text" id="name"><br><br>

            Email:<br>
            <input type="text" id="email"><br><br>

            Marks:<br>
            <input type="number" id="marks"><br><br>

            <button onclick="saveStudent()">Save</button>
            <button onclick="closeModal()">Cancel</button>

        </div>

        <script>

            let studentsData = [];


            function loadStudents() {

                fetch("api/students")
                        .then(res => res.json())
                        .then(data => {
                            studentsData = data;
                            displayStudents(data);
                        });
            }


            function displayStudents(data) {

                let table = document.getElementById("studentTable");
                table.innerHTML = "";

                data.forEach(student => {

                    let row = "<tr>" +
                            "<td>" + student.id + "</td>" +
                            "<td>" + student.name + "</td>" +
                            "<td>" + student.email + "</td>" +
                            "<td>" + student.marks + "</td>" +
                            "<td>" +
                            "<button onclick='editStudent(" + student.id + ")'>Edit</button> " +
                            "<button onclick='deleteStudent(" + student.id + ")'>Delete</button>" +
                            "</td>" +
                            "</tr>";

                    table.innerHTML += row;
                });
            }


            function getStudentById() {

                let id = document.getElementById("searchId").value;

                fetch("api/students?id=" + id)
                        .then(res => res.json())
                        .then(data => {

                            if (data.message) {
                                alert(data.message);
                            } else {
                                displayStudents([data]);
                            }
                        });
            }


            function openModal() {

                document.getElementById("modalTitle").innerText = "Add Student";
                document.getElementById("studentId").value = "";
                document.getElementById("name").value = "";
                document.getElementById("email").value = "";
                document.getElementById("marks").value = "";
                document.getElementById("studentModal").style.display = "block";
            }

        
            function closeModal() {
                document.getElementById("studentModal").style.display = "none";
            }


            function saveStudent() {

                let id = document.getElementById("studentId").value;
                let name = document.getElementById("name").value;
                let email = document.getElementById("email").value;
                let marks = document.getElementById("marks").value;

                if (id === "") {

                    fetch("api/student", {
                        method: "POST",
                        headers: {"Content-Type": "application/json"},
                        body: JSON.stringify({
                            name: name,
                            email: email,
                            marks: marks
                        })
                    })
                            .then(res => res.json())
                            .then(data => {
                                alert(data.message);
                                closeModal();
                                loadStudents();
                            });

                } else {

                    // PUT - Update
                    fetch("api/student?id=" + id, {
                        method: "PUT",
                        headers: {"Content-Type": "application/json"},
                        body: JSON.stringify({
                            email: email,
                            marks: marks
                        })
                    })
                            .then(res => res.json())
                            .then(data => {
                                alert("Updated Successfully");
                                closeModal();
                                loadStudents();
                            });
                }
            }

            function editStudent(id) {

                let student = studentsData.find(s => s.id === id);

                document.getElementById("modalTitle").innerText = "Update Student";
                document.getElementById("studentId").value = student.id;
                document.getElementById("name").value = student.name;
                document.getElementById("email").value = student.email;
                document.getElementById("marks").value = student.marks;

                document.getElementById("studentModal").style.display = "block";
            }

            function deleteStudent(id) {

                fetch("api/student?id=" + id, {
                    method: "DELETE"
                })
                        .then(res => res.json())
                        .then(data => {
                            alert(data.message);
                            loadStudents();
                        });
            }

            document.addEventListener("DOMContentLoaded", loadStudents);

        </script>

    </body>
</html>
