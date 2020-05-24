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
    private final long totalItems;
    private final long totalPages;

    @JsonCreator
    public PageDto(
            @JsonProperty("content") final List<T> content,
            @JsonProperty("totalItems") final long totalItems,
            @JsonProperty("totalPages") final long totalPages
    ) {
        this.content = content;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }

    public static <F, T> PageDto<T> from(final Page<F> page, final Function<F, T> converter) {
        final List<F> content = page.getContent();
        final long totalItems = page.getTotalElements();
        final int totalPages = page.getTotalPages();

        return new PageDto<>(content.stream().map(converter).collect(Collectors.toList()), totalItems, totalPages);
    }
}
