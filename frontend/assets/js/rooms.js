function getAllRooms() {
    cleanRoomList()
    $.ajax({
        url: Config().API_URL + '/rooms',
        error: function() {
            $("#room-list").append(`
                <div class="alert alert-warning" role="alert">
                    Could not load rooms, please try again later!
                </div>
            `)
        },
        success: function(data, status) {
            if(data.length != 0) {
                for (const key in data) {
                    let room = data[key]
                    $("#room-list").append(`
                        <li class="list-group-item d-flex justify-content-between align-items-start">
                            <div class="ms-2 me-auto">
                                <p class="fw-bold">` + room.name + `</p>
                                <p>Seats: `+ room.seats + `</p>
                                <p>Room type: ` + room.type + `</p>
                                <button type="button" class="btn btn-warning">Edit</button>
                                <button type="button" class="btn btn-danger" onclick="removeRoom(` + room.id + `)">Delete</button>
                            </div>
                        </li>
                    `)
                }
            } else {
                $("#room-list").append(`
                    <div class="alert alert-light" role="alert">
                        No rooms available!
                    </div>
                `)
            }
        }
    })
}

function removeRoom(id) {
    $.ajax({
        url: Config().API_URL + '/rooms/'+ id,
        type: "DELETE",
        error: function() {
            alert("Could not delete room")
        },
        success: function(data, status) {
            alert("Room deleted")
            getAllRooms()
        }
    })
}

function cleanRoomList() {
    $("#room-list").empty()
}
