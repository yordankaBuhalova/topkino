package fmi.softech.topkino.mappers;

import fmi.softech.topkino.dtos.ProjectionDto;
import fmi.softech.topkino.exceptions.NotFoundException;
import fmi.softech.topkino.models.Movie;
import fmi.softech.topkino.models.Projection;
import fmi.softech.topkino.models.Room;
import fmi.softech.topkino.services.MovieService;
import fmi.softech.topkino.services.RoomService;

import java.util.ArrayList;
import java.util.List;

public class ProjectionMapper {
    private final RoomService roomService;
    private final MovieService movieService;

    public ProjectionMapper(RoomService roomService, MovieService movieService) {
        this.roomService = roomService;
        this.movieService = movieService;
    }

    public ProjectionDto entityToDto(Projection projection) {
        ProjectionDto projectionDto = new ProjectionDto();

        if(projection != null) {
            projectionDto.setId(projection.getId());
            projectionDto.setMovie(projection.getMovie().getId());
            projectionDto.setRoom(projection.getRoom().getId());
            projectionDto.setPrice(projection.getPrice());
            projectionDto.setProjectionOn(projection.getProjectionOn());
        }

        return projectionDto;
    }

    public Projection dtoToEntity(ProjectionDto projectionDto) {
        Projection projection = new Projection();

        try {
            if(projectionDto != null) {
                Room room = null;
                Movie movie = null;
                if(projectionDto.getRoom() != null) {
                    room = roomService.getOneById(projectionDto.getRoom());
                }
                if (projectionDto.getMovie() != null) {
                     movie = movieService.getOneById(projectionDto.getMovie());
                }

                projection.setId(projectionDto.getId());
                projection.setMovie(movie);
                projection.setRoom(room);
                projection.setPrice(projectionDto.getPrice());
                projection.setProjectionOn(projectionDto.getProjectionOn());
            }

        } catch(NotFoundException e) {
            System.err.println(e.getMessage());
        }

        return projection;
    }

    public List<ProjectionDto> entityListToDtoList(List<Projection> projections) {
        List<ProjectionDto> projectionDtos = new ArrayList<>();
        for (Projection pr:projections) {
            projectionDtos.add(entityToDto(pr));
        }
        return projectionDtos;
    }
}
