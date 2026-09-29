document.addEventListener('DOMContentLoaded', function () {
    const dniInput = document.getElementById('dniLector');
    const nombreInput = document.getElementById('nombreLector');
    const boxEstadoLector = document.getElementById('boxEstadoLector');
    const iconoEstadoLector = document.getElementById('iconoEstadoLector');
    const textoEstadoLector = document.getElementById('textoEstadoLector');
    const subtextoEstadoLector = document.getElementById('subtextoEstadoLector');
    const alertaLectorError = document.getElementById('alertaLectorError');
    const msjLectorError = document.getElementById('msjLectorError');

    const selectLibro = document.getElementById('selectLibro');
    const isbnInput = document.getElementById('isbnLibro');
    const alertaLibro = document.getElementById('alertaLibroInvalido');
    const boxStock = document.getElementById('badgeStockVerificado');

    const selectModalidad = document.getElementById('selectModalidad');
    const optDomicilio = document.getElementById('optDomicilio');
    const optSala = document.getElementById('optSala');
    const alertaTesis = document.getElementById('alertaTesisSala');

    const fEmision = document.getElementById('fechaEmision');
    const fLimite = document.getElementById('fechaLimite');
    const alertaDias = document.getElementById('alertaExcesoDias');

    const btnSubmit = document.getElementById('btnRegistrarPrestamo');
    const lectores = window.listaLectoresRegistrados || [];

    function verificarRestriccionCategoria() {
        if (!selectLibro || !selectModalidad) return;

        const opt = selectLibro.options[selectLibro.selectedIndex];
        const categoria = (opt ? opt.getAttribute('data-categoria') || '' : '').toLowerCase();

        const esConsultaExclusiva = categoria.includes('tesis') || categoria.includes('referencia') || categoria.includes('fondo reservado');

        if (esConsultaExclusiva) {
            selectModalidad.value = "Lectura en Sala";
            if (optDomicilio) optDomicilio.disabled = true;
            if (alertaTesis) alertaTesis.classList.remove('d-none');
        } else {
            if (optDomicilio) optDomicilio.disabled = false;
            if (alertaTesis) alertaTesis.classList.add('d-none');
        }
    }

    function validarLector() {
        const dni = dniInput ? dniInput.value.trim() : '';

        if (!dni) {
            if (nombreInput) nombreInput.value = '';
            if (boxEstadoLector) boxEstadoLector.className = 'alert alert-secondary py-2 px-3 mb-0 d-flex align-items-center';
            if (iconoEstadoLector) iconoEstadoLector.className = 'bi bi-info-circle-fill fs-5 me-2 text-secondary';
            if (textoEstadoLector) textoEstadoLector.textContent = 'Ingrese DNI del lector';
            if (subtextoEstadoLector) subtextoEstadoLector.textContent = 'Pendiente de verificación';
            if (alertaLectorError) alertaLectorError.classList.add('d-none');
            return false;
        }

        const lector = lectores.find(l => l.dni === dni || l.documento === dni);

        if (!lector) {
            if (nombreInput) nombreInput.value = '';
            if (boxEstadoLector) boxEstadoLector.className = 'alert alert-danger py-2 px-3 mb-0 d-flex align-items-center';
            if (iconoEstadoLector) iconoEstadoLector.className = 'bi bi-x-circle-fill fs-5 me-2 text-danger';
            if (textoEstadoLector) textoEstadoLector.textContent = 'Lector no encontrado';
            if (subtextoEstadoLector) subtextoEstadoLector.textContent = 'El documento ingresado no existe';
            if (msjLectorError) msjLectorError.textContent = 'El DNI no pertenece a ningún usuario registrado en el sistema.';
            if (alertaLectorError) alertaLectorError.classList.remove('d-none');
            return false;
        }

        if (nombreInput) nombreInput.value = lector.nombresApellidos || lector.nombre || '';

        const estado = lector.estadoCuenta || lector.estado || '';
        const conSancion = estado.includes('Mora') || estado.includes('Sanción');
        const limiteAlcanzado = (lector.prestamosActivos >= 3) || estado.includes('Límite');

        if (conSancion || limiteAlcanzado) {
            if (boxEstadoLector) boxEstadoLector.className = 'alert alert-danger py-2 px-3 mb-0 d-flex align-items-center';
            if (iconoEstadoLector) iconoEstadoLector.className = 'bi bi-shield-x fs-5 me-2 text-danger';
            if (textoEstadoLector) textoEstadoLector.textContent = conSancion ? 'Con Mora / Sanción' : 'Límite alcanzado (3/3)';
            if (subtextoEstadoLector) subtextoEstadoLector.textContent = conSancion ? 'Usuario inhabilitado por penalización' : 'No puede solicitar más ejemplares';
            if (msjLectorError) msjLectorError.textContent = conSancion 
                ? 'El usuario registra sanciones activas y no puede solicitar libros.' 
                : 'El usuario ya tiene el máximo permitido de préstamos activos (3/3).';
            if (alertaLectorError) alertaLectorError.classList.remove('d-none');
            return false;
        }

        if (boxEstadoLector) boxEstadoLector.className = 'alert alert-success py-2 px-3 mb-0 d-flex align-items-center';
        if (iconoEstadoLector) iconoEstadoLector.className = 'bi bi-check-circle-fill fs-5 me-2 text-success';
        if (textoEstadoLector) textoEstadoLector.textContent = 'Habilitado para préstamo';
        if (subtextoEstadoLector) subtextoEstadoLector.textContent = (lector.prestamosActivos !== undefined) ? `Préstamos activos: ${lector.prestamosActivos}/3` : 'Sin penalizaciones pendientes';
        if (alertaLectorError) alertaLectorError.classList.add('d-none');
        return true;
    }

    function validarTodo() {
        const lectorValido = validarLector();

        let libroValido = false;
        if (selectLibro) {
            const opt = selectLibro.options[selectLibro.selectedIndex];
            const stock = parseInt(opt ? opt.getAttribute('data-stock') : '0', 10);
            if (selectLibro.value !== '' && stock > 0) {
                libroValido = true;
                if (alertaLibro) alertaLibro.classList.add('d-none');
                if (boxStock) boxStock.classList.remove('d-none');
            } else {
                if (alertaLibro) alertaLibro.classList.remove('d-none');
                if (boxStock) boxStock.classList.add('d-none');
            }
        }

        let fechaValida = true;
        if (fEmision && fLimite && fEmision.value && fLimite.value) {
            const d1 = new Date(fEmision.value);
            const d2 = new Date(fLimite.value);
            const dias = Math.ceil((d2 - d1) / (1000 * 3600 * 24));
            if (dias > 15 || dias < 0) {
                fechaValida = false;
                if (alertaDias) alertaDias.classList.remove('d-none');
                fLimite.classList.add('is-invalid');
            } else {
                if (alertaDias) alertaDias.classList.add('d-none');
                fLimite.classList.remove('is-invalid');
            }
        }

        if (btnSubmit) {
            btnSubmit.disabled = !(lectorValido && libroValido && fechaValida);
        }
    }

    if (fEmision && !fEmision.value) {
        const hoy = new Date().toISOString().split('T')[0];
        fEmision.value = hoy;
        const limiteSugerido = new Date();
        limiteSugerido.setDate(limiteSugerido.getDate() + 15);
        if (fLimite) fLimite.value = limiteSugerido.toISOString().split('T')[0];
    }

    if (dniInput) {
        dniInput.addEventListener('input', validarTodo);
        dniInput.addEventListener('change', validarTodo);
    }

    if (selectLibro) {
        selectLibro.addEventListener('change', function () {
            const opt = this.options[this.selectedIndex];
            if (opt && opt.getAttribute('data-isbn') && isbnInput) {
                isbnInput.value = opt.getAttribute('data-isbn');
            }
            verificarRestriccionCategoria();
            validarTodo();
        });
    }

    if (isbnInput && selectLibro) {
        const sincronizarIsbnASelect = function () {
            const isbnIngresado = isbnInput.value.trim().toLowerCase();
            let encontrado = false;

            for (let i = 0; i < selectLibro.options.length; i++) {
                const opt = selectLibro.options[i];
                const isbnOpt = (opt.getAttribute('data-isbn') || '').trim().toLowerCase();

                if (isbnOpt !== '' && isbnOpt === isbnIngresado) {
                    selectLibro.selectedIndex = i;
                    encontrado = true;
                    break;
                }
            }

            if (!encontrado && isbnIngresado !== '') {
                selectLibro.selectedIndex = 0;
            }

            if (typeof verificarRestriccionCategoria === 'function') {
                verificarRestriccionCategoria();
            }
            validarTodo();
        };

        isbnInput.addEventListener('input', sincronizarIsbnASelect);
        isbnInput.addEventListener('change', sincronizarIsbnASelect);
    }

    if (fEmision) fEmision.addEventListener('change', validarTodo);
    if (fLimite) fLimite.addEventListener('change', validarTodo);

    const formPrestamo = document.getElementById('formPrestamo');
    if (formPrestamo) {
        formPrestamo.addEventListener('reset', function () {
            setTimeout(function () {
                const hoy = new Date().toISOString().split('T')[0];
                if (fEmision) fEmision.value = hoy;
                const limiteSugerido = new Date();
                limiteSugerido.setDate(limiteSugerido.getDate() + 15);
                if (fLimite) fLimite.value = limiteSugerido.toISOString().split('T')[0];
                verificarRestriccionCategoria();
                validarTodo();
            }, 20);
        });
    }

    verificarRestriccionCategoria();
    validarTodo();
});
