package spharos.msg.domain.option.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spharos.msg.domain.option.repository.OptionRepository;

@Service
@RequiredArgsConstructor
public class OptionService {
    private final OptionRepository optionRepository;
}
