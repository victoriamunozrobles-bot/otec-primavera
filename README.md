# 🎓 Plataforma de Gestión Académica - OTEC "Primavera" (MVP)

Un Producto Mínimo Viable (MVP) desarrollado para centralizar, digitalizar y optimizar la gestión administrativa y académica de la OTEC Primavera.

## 📖 El Problema
La OTEC Primavera experimentaba un rápido crecimiento que hizo insostenible la gestión manual de matrículas, cursos y calificaciones. Se necesitaba una solución tecnológica que proporcionara trazabilidad en tiempo real para los administradores y empoderara a los estudiantes con un portal de autogestión claro y accesible.

## 💡 La Solución
Se diseñó una plataforma web basada en roles que separa las responsabilidades de los usuarios para maximizar la usabilidad:
* **Para la Administración:** Un panel de control y un "Libro de Clases" que permite visualizar e ingresar calificaciones de forma masiva, rápida y segura, previniendo errores de digitación.
* **Para el Estudiante:** Un "Portal Académico" y una "Sala de Clases" virtual por módulo, diseñados para reducir la carga cognitiva y enfocar al alumno puramente en su aprendizaje y retroalimentación.

## 🛠️ Stack Tecnológico
Este proyecto fue construido integrando el ecosistema de Spring Boot para asegurar escalabilidad y robustez:
* **Backend:** Java 17, Spring Boot, Spring MVC.
* **Persistencia:** Spring Data JPA, Hibernate, MariaDB.
* **Seguridad:** Spring Security (Gestión de roles ADMIN/ESTUDIANTE y encriptación de contraseñas con BCrypt).
* **Frontend:** HTML5, CSS3, Bootstrap 5, Thymeleaf.
* **Gestor de Dependencias:** Maven.

## 🏗️ Decisiones de Arquitectura y Diseño
1.  **Vistas Tontas, Controladores Inteligentes:** Durante el desarrollo, se observó que delegar cálculos complejos (como la renderización de la matriz bidimensional de calificaciones) al motor de plantillas (Thymeleaf) generaba cuellos de botella y errores 500 (`response has already been committed`). Se refactorizó la arquitectura para procesar diccionarios de datos (Maps) directamente en el backend (Java), entregando a la vista información pre-digerida. Esto aumentó drásticamente el rendimiento y la estabilidad.
2.  **Integridad Referencial Segura:** Se implementaron borrados lógicos y desvinculaciones en cascada mediante métodos `@Transactional`. Por ejemplo, al eliminar un curso, el sistema automáticamente desmatricula a los alumnos y limpia las evaluaciones huérfanas sin romper la base de datos.
3.  **Preparación para Escalabilidad (API REST):** Se implementaron endpoints en formato JSON (`/api/v1/cursos` y `/api/v1/estudiantes`) para sentar las bases de futuras integraciones, como una aplicación móvil o servicios de reportería externos.

## 🌱 Oportunidades de Mejora y Próximos Pasos
Este proyecto representa un MVP funcional, pero como todo producto de software iterativo, existen áreas clave identificadas para el futuro crecimiento de la plataforma:
* **Sistema de Archivos (BLOB/S3):** Implementar la carga y descarga de material de estudio (PDFs, documentos) directamente en los módulos. La interfaz gráfica ya se encuentra preparada para recibir esta funcionalidad en el próximo Sprint.
* **Manejo Global de Excepciones:** Migrar el manejo de errores locales a un `@ControllerAdvice` global para centralizar la respuesta ante fallos imprevistos y mejorar la experiencia del usuario con páginas de error personalizadas.
* **Paginación y Filtros:** A medida que la base de datos de estudiantes y cursos crezca, será necesario implementar `Pageable` de Spring Data JPA en los listados principales para mantener tiempos de carga óptimos.
* **Desacoplamiento del Frontend:** A largo plazo, evaluar la migración de las vistas Thymeleaf a un framework reactivo (como React o Vue.js), consumiendo la API REST que ya comenzó a construirse.

