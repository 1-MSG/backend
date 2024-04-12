package spharos.msg.domain.options.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spharos.msg.domain.options.dto.OptionTypeDto;
import spharos.msg.domain.options.entity.Options;

import java.util.List;

public interface OptionsRepository extends JpaRepository<Options, Long> {
    List<Options> findOptionsByParentId(Long parentId);

    @Query("select o.optionLevel,o.optionType from Options o where o= :options")
    OptionTypeDto findOptionsType(@Param("options")Options options);
}
