package eu.navads.nightjar.endpoint;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class PageDto<T> {

    private final List<T> data;
    private final long draw;
    private final long recordsTotal;
    private final long recordsFiltered;

    @JsonCreator
    public PageDto(
            @JsonProperty("data") final List<T> data,
            @JsonProperty("draw") final long draw,
            @JsonProperty("recordsTotal") final long recordsTotal,
            @JsonProperty("recordsFiltered") final long recordsFiltered
    ) {
        this.data = data;
        this.draw = draw;
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsFiltered;
    }

    public static <F, T> PageDto<T> from(final Page<F> page, final Function<F, T> converter, final int draw) {
        final List<F> data = page.getContent();
        final long recordsTotal = page.getTotalElements();

        return new PageDto<>(
                data.stream().map(converter).collect(Collectors.toList()),
                draw,
                recordsTotal,
                recordsTotal
        );
    }
}
