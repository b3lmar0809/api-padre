package com.barrioapp.api_padre.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-ventas-inventario", url = "${ms-ventas-inventario.url}")
public interface MsVentasInventarioClient {

    @GetMapping("/productos/{usuarioId}/count")
    Integer getActiveProductCount(@PathVariable Long usuarioId);
}
