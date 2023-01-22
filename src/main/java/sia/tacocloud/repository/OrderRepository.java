package sia.tacocloud.repository;

import sia.tacocloud.dto.TacoOrder;

public interface OrderRepository {
    TacoOrder save(TacoOrder order);
}
