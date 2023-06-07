package kyonggi.cspop.application.util.common;

import org.springframework.stereotype.Component;

@Component
public class PageHandler {

    private final static int PAGE_BLOCK = 10;
    private int startBlockPage;
    private int endBlockPage;

    public int[] getStartAndEndBlockPage(int pageNumber, int totalPages) {
        startBlockPage = (pageNumber / PAGE_BLOCK) * PAGE_BLOCK + 1;
        endBlockPage = Math.min(startBlockPage + PAGE_BLOCK - 1, totalPages);
        return new int[]{startBlockPage, endBlockPage};
    }
}
