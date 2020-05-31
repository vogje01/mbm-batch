package com.hlag.fis.batch.manager.service.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Paging utilities.
 *
 * @author Jens Vogt (jens.vogt@ext.hlag.com)
 * @since 0.0.3
 */
public class PagingUtil {

    /**
     * Convert single paging/sorting values into a pageable.
     *
     * @param page    page number.
     * @param size    page size.
     * @param sortBy  sorting column.
     * @param sortDir sort direction.
     * @return pageable object.
     */
    public static Pageable getPageable(int page, int size, String sortBy, String sortDir) {
        if (size <= 0) {
            size = Integer.MAX_VALUE;
        }
        Sort sorting = Sort.unsorted();
        if (sortBy != null) {
            sorting = Sort.by(sortBy);
        }
        if (sortDir != null) {
            sorting = sortDir.equals("asc") ? sorting.ascending() : sorting.descending();
        }
        return PageRequest.of(page, size, sorting);
    }
}
