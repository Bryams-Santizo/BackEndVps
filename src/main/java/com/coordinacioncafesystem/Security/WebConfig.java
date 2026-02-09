package com.coordinacioncafesystem.Security;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Inyectamos la ruta de la carpeta de subidas
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 1. Obtener la ruta absoluta de la carpeta de subida.
        //    System.getProperty("user.dir") da la ruta raíz del proyecto.
        //    Se usa replaceAll para asegurar que las barras sean correctas en todos los SOs.
        String rootPath = System.getProperty("user.dir").replaceAll("\\\\", "/");

        // 2. Construir la ruta final como file:///RUTA_ABSOLUTA/uploads/
        String absoluteUploadPath = "file:///" + rootPath + "/" + uploadDir + "/";

        // Mapeamos la URL /uploads/** a la ruta física del proyecto.
        //
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(absoluteUploadPath);

        // Opcional: Para verificar en logs la ruta que se está usando
        System.out.println("DEBUG: Mapeando /uploads/** a la ruta física: " + absoluteUploadPath);
    }
}