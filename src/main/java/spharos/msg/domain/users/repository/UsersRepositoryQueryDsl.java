package spharos.msg.domain.users.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import spharos.msg.domain.admin.dto.AdminResponseDto;

public interface UsersRepositoryQueryDsl {
    List<AdminResponseDto.SearchInfo> findUsersByPageable(Pageable pageable);
}
