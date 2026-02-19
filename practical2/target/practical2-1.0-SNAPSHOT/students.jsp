<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Students AJAX JSON Page</title>

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

        <h2>Students Management (AJAX)</h2>

        <!-- Search -->
        <input type="text" id="searchInput" placeholder="Search by name..." onkeyup="filterStudents()">

        <!-- Add Button -->
        <button onclick="openModal()">Add Student</button>

        <br><br>

        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Course</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody id="studentTable"></tbody>
        </table>

        <!-- Modal Form -->
        <div id="studentModal" class="modal">
            <h3 id="modalTitle">Add Student</h3>

            <input type="hidden" id="studentId">

            Name:<br>
            <input type="text" id="name"><br><br>

            Course:<br>
            <input type="text" id="course"><br><br>

            <button onclick="saveStudent()">Save</button>
            <button onclick="closeModal()">Cancel</button>
        </div>

        <script>

            let studentsData = [];

            function loadStudents() {

                fetch("studentAjax")
                        .then(response => response.json())
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
                            "<td>" + student.course + "</td>" +
                            "<td>" +
                            "<button onclick='editStudent(" + student.id + ")'>Edit</button> " +
                            "<button onclick='deleteStudent(" + student.id + ")'>Delete</button>" +
                            "</td>" +
                            "</tr>";

                    table.innerHTML += row;
                });
            }

            function filterStudents() {
                let keyword = document.getElementById("searchInput").value.toLowerCase();

                let filtered = studentsData.filter(s =>
                    s.name.toLowerCase().includes(keyword)
                    
                );

                displayStudents(filtered);
            }

            function openModal() {
                document.getElementById("modalTitle").innerText = "Add Student";
                document.getElementById("studentId").value = "";
                document.getElementById("name").value = "";
                document.getElementById("course").value = "";
                document.getElementById("studentModal").style.display = "block";
            }

            function closeModal() {
                document.getElementById("studentModal").style.display = "none";
            }

            function saveStudent() {

                let id = document.getElementById("studentId").value;
                let name = document.getElementById("name").value;
                let course = document.getElementById("course").value;

                let url = id ? "updateStudentAjax" : "addStudentAjax";

                fetch(url, {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    },
                    body: "id=" + id + "&name=" + name + "&course=" + course
                })
                        .then(response => response.json())
                        .then(data => {
                            alert("Saved Successfully");
                            closeModal();
                            loadStudents();
                        });
            }

            function editStudent(id) {

                let student = studentsData.find(s => s.id === id);

                document.getElementById("modalTitle").innerText = "Update Student";
                document.getElementById("studentId").value = student.id;
                document.getElementById("name").value = student.name;
                document.getElementById("course").value = student.course;

                document.getElementById("studentModal").style.display = "block";
            }

            function deleteStudent(id) {

                fetch("deleteStudentAjax", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    },
                    body: "id=" + id
                })
                        .then(response => response.json())
                        .then(data => {
                            alert("Deleted Successfully");
                            loadStudents();
                        });
            }

            document.addEventListener("DOMContentLoaded", function () {
                loadStudents();
            });

        </script>

    </body>
</html>
