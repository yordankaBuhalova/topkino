function getAllProjections() {
    $.ajax({
        url: Config().API_URL + '/projections',
        error: function() {
            $("#projection-list").append(`
                <div class="alert alert-warning" role="alert">
                    Could not load projections, please try again later!
                </div>
            `)
        },
        success: function(data, status) {
            if(data.length != 0) {
                for (const key in data) {
                    let projection = data[key]
                    $("#projection-list").append(`
                        <li class="list-group-item d-flex justify-content-between align-items-start">
                            <div class="ms-2 me-auto">
                                <p class="fw-bold">` + projection.movie.name + `</p>
                                <p>Room: `+ projection.projection_on + `</p>
                                <p>Room: `+ projection.room.name + `</p>
                                <p>price: ` + projection.price + `</p>
                                <button type="button" class="btn btn-warning">Edit</button>
                                <button type="button" class="btn btn-danger">Delete</button>
                            </div>
                            <button type="button" class="btn btn-primary">Make reservation</button>
                        </li>
                    `)
                }
            } else {
                $("#projection-list").append(`
                    <div class="alert alert-light" role="alert">
                        No projections available!
                    </div>
                `)
            }
        }
    })
}