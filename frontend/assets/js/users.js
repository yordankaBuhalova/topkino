function getAllUsers() {
    cleanUserList()
    $.ajax({
        url: Config().API_URL + '/users',
        error: function() {
            $("#users-list").append(`
                <div class="alert alert-warning" role="alert">
                    Could not load users, please try again later!
                </div>
            `)
        },
        success: function(data, status) {
            if(data.length != 0) {
                for (const key in data) {
                    let user = data[key]
                    $("#users-list").append(`
                        <li class="list-group-item d-flex justify-content-between align-items-start">
                            <div class="ms-2 me-auto">
                                <p class="fw-bold">` + user.username + `</p>
                                <p>Admin: `+ user.admin + `</p>

                                <button type="button" class="btn btn-warning">Edit</button>
                                <button type="button" class="btn btn-danger" onclick="removeUser(` + user.id + `)">Delete</button>
                            </div>
                        </li>
                    `)
                }
            } else {
                $("#users-list").append(`
                    <div class="alert alert-light" role="alert">
                        No users available!
                    </div>
                `)
            }
        }
    })
}


function removeUser(id) {
    $.ajax({
        url: Config().API_URL + '/users/'+ id,
        type: "DELETE",
        error: function() {
            alert("Could not delete user")
        },
        success: function(data, status) {
            alert("User deleted")
            getAllUsers()
        }
    })
}

function cleanUserList() {
    $("#users-list").empty()
}
