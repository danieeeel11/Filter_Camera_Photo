# Filter_Camera_Photo

Este repositorio contiene la implementación de una aplicación de Android que permite aplicar un filtro negativo o un filtro de realce de bordes a imágenes capturadas con la cámara del dispositivo o seleccionadas desde la galería. Utilizando técnicas de procesamiento de imágenes, la aplicación transforma las imágenes según el filtro seleccionado por el usuario.

## Contenido

1. [Abstract](#abstract)
2. [Tecnologías Utilizadas](#Tecnologías_Utilizadas)
3. [Implementación](#Implementación)
4. [Referencias](#Referencias)

## Abstract
Aplicar filtros a imágenes es un desafío que se puede abordar eficazmente mediante técnicas de procesamiento de imágenes. Esta aplicación de Android permite a los usuarios aplicar un filtro negativo o un filtro de realce de bordes a imágenes capturadas por la cámara del dispositivo o seleccionadas desde la galería. La implementación incluye el uso de OpenCV para realizar las transformaciones necesarias, proporcionando una herramienta versátil y fácil de usar para la edición de imágenes.

## Tecnologías_Utilizadas
* Java/Kotlin: Lenguaje de programación principal para la implementación de la aplicación.
* OpenCV: Biblioteca de procesamiento de imágenes utilizada para aplicar los filtros.
* Android Studio: Entorno de desarrollo integrado (IDE) para la construcción de la aplicación.

## Implementación
La implementación del proyecto se divide en varias etapas:
* Captura de Imagen: Uso de la cámara del dispositivo para capturar imágenes o selección de imágenes desde la galería.
* Preprocesamiento de Imagen: Aplicación de técnicas de procesamiento de imágenes para preparar la imagen para el filtrado.
* Aplicación de Filtros:
  * Filtro Negativo: Inversión de los colores de la imagen para crear un efecto negativo.
  * Filtro de Realce de Bordes: Aplicación de técnicas de detección de bordes para resaltar los contornos en la imagen.
* Visualización de Resultados: Mostrar la imagen con el filtro aplicado en la pantalla del dispositivo.

## Referencias
OpenCV Documentation: https://docs.opencv.org/
