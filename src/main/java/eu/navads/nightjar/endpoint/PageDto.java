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

    private final List<T> content;
    private final long totalSize;

    @JsonCreator
    public PageDto(
            @JsonProperty("content") final List<T> content,
            @JsonProperty("totalSize") final long totalSize
    ) {
        this.content = content;
        this.totalSize = totalSize;
    }

    public static <F, T> PageDto<T> from(final Page<F> page, final Function<F, T> converter) {
        final List<F> content = page.getContent();
        final long totalSize = page.getTotalElements();

        return new PageDto<>(
                content.stream().map(converter).collect(Collectors.toList()),
                totalSize
        );
    }
}
