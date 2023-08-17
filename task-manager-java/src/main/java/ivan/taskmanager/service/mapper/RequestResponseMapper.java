package ivan.taskmanager.service.mapper;

public interface RequestResponseMapper<Q, S, M> {
    M toModel(Q dto);

    S toDto(M model);
}
