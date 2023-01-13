function getAllMovies() {
    cleanMovieList()
    $.ajax({
        url: Config().API_URL + '/movies',
        error: function() {
            $("#movie-list").append(`
                <div class="alert alert-warning" role="alert">
                    Could not load movies, please try again later!
                </div>
            `)
        },
        success: function(data, status) {
            if(data.length != 0) {
                for (const key in data) {
                    let movie = data[key]
                    $("#movie-list").append(`
                        <li class="list-group-item d-flex justify-content-between align-items-start">
                            <div class="col-ms-2">
                                <img src="" class="img-fluid rounded-start" alt="...">
                            </div>
                            <div class="ms-2 me-auto">
                                <div class="fw-bold">` + movie.title + `</div>
                                <small class="d-flex fst-italic fw-lighter text-wrap ">
                                Genre: ` + movie.genre + `
                                </small>
                                <small class="d-flex fst-italic fw-lighter text-wrap ">
                                Duration: ` + movie.duration + `
                                </small>

                                <small class="d-flex fst-italic fw-lighter text-wrap ">
                                Release Year: ` + movie.releaseYear + `
                                </small>
                                <small class="d-flex fst-italic fw-lighter text-wrap ">
                                Language: ` + movie.language + `
                                </small>
                                <small class="d-flex fst-italic fw-lighter text-wrap ">
                                Trailer: ` + movie.trailerUrl + `
                                </small>

                                <p>
                                    <button class="btn btn-link" type="button" data-bs-toggle="collapse" data-bs-target="#movieDesc`+ key +`" aria-expanded="false" aria-controls="movieDesc`+ key +`">
                                        Description
                                    </button>
                                </p>
                                <div class="collapse" id="movieDesc`+ key +`">
                                    <div class="card card-body">
                                        `+ movie.description +`
                                    </div>
                                </div>
                                <button type="button" class="btn btn-warning">Edit</button>
                                <button type="button" class="btn btn-danger" onclick="removeMovie(` + movie.id + `)">Delete</button>
                            </div>
                        </li>
                    `)
                }
            } else {
                $("#movie-list").append(`
                    <div class="alert alert-light" role="alert">
                        No movies available!
                    </div>
                `)
            }
        }
    })
}

function removeMovie(id) {
    $.ajax({
        url: Config().API_URL + '/movies/'+ id,
        type: "DELETE",
        error: function() {
            alert("Could not delete movie")
        },
        success: function(data, status) {
            alert("Movie deleted")
            getAllMovies()
        }
    })
}

function cleanMovieList() {
    $("#movie-list").empty()
}
