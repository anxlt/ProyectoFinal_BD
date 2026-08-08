package logico;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import db.CandidatoDAOImpl;
import db.CentroEmpleadorDAOImpl;
import db.OfertaLaboralDAOImpl;
import db.SolicitudDAOImpl;
import db.UsuarioDAOImpl;
import db.VacanteCompletadaDAOImpl;
import exception.NotRemovableException;

public class BolsaLaboral implements Serializable {

	private static final long serialVersionUID = 1L;

	private ArrayList<Candidato> candidatos;
	private ArrayList<Solicitud> solicitudes;
	private ArrayList<OfertaLaboral> ofertas;
	private ArrayList<CentroEmpleador> centros;
	private ArrayList<VacanteCompletada> vacantes;
	private ArrayList<Usuario> usuarios;

	public static BolsaLaboral instancia;
	private Usuario usuarioActual;

	private BolsaLaboral() {
		candidatos = new ArrayList<>();
		solicitudes = new ArrayList<>();
		ofertas = new ArrayList<>();
		centros = new ArrayList<>();
		vacantes = new ArrayList<>();
		usuarios = new ArrayList<>();
	}

	public ArrayList<Candidato> getCandidatos() { return candidatos; }
	public void setCandidatos(ArrayList<Candidato> candidatos) { this.candidatos = candidatos; }

	public ArrayList<Solicitud> getSolicitudes() { return solicitudes; }
	public void setSolicitudes(ArrayList<Solicitud> solicitudes) { this.solicitudes = solicitudes; }

	public ArrayList<OfertaLaboral> getOfertas() { return ofertas; }
	public void setOfertas(ArrayList<OfertaLaboral> ofertas) { this.ofertas = ofertas; }

	public ArrayList<CentroEmpleador> getCentros() { return centros; }
	public void setCentros(ArrayList<CentroEmpleador> centros) { this.centros = centros; }

	public ArrayList<VacanteCompletada> getVacantes() { return vacantes; }
	public void setVacantes(ArrayList<VacanteCompletada> vacantes) { this.vacantes = vacantes; }

	public ArrayList<Usuario> getUsuarios() { return usuarios; }
	public void setUsuarios(ArrayList<Usuario> usuarios) { this.usuarios = usuarios; }

	public Usuario getUsuarioActual() { return usuarioActual; }
	public void setUsuarioActual(Usuario usuarioActual) { this.usuarioActual = usuarioActual; }

	public static BolsaLaboral getInstancia() {
		if (instancia == null) {
			instancia = new BolsaLaboral();
		}
		return instancia;
	}

	public static void setInstancia(BolsaLaboral bolsa) {
		instancia = bolsa;
	}

	public void cargarCentrosDesdeBD() {
		centros = (ArrayList<CentroEmpleador>) new CentroEmpleadorDAOImpl().listarTodos();
	}

	public void registrarCentroTrabajo(CentroEmpleador nuevoCentro) {
		centros.add(nuevoCentro);
		new CentroEmpleadorDAOImpl().insertar(nuevoCentro);
	}

	public int buscarIndiceCentroByCodigo(String codigo) {
		int indice = 0;
		boolean encontrado = false;
		while (!encontrado && indice < centros.size()) {
			if (centros.get(indice).getCodigo().equalsIgnoreCase(codigo)) {
				encontrado = true;
			} else {
				indice++;
			}
		}
		return encontrado ? indice : -1;
	}

	public boolean modificarCentroTrabajo(CentroEmpleador centroModificar) {
		int indice = buscarIndiceCentroByCodigo(centroModificar.getCodigo());
		if (indice != -1) {
			centros.set(indice, centroModificar);
			new CentroEmpleadorDAOImpl().actualizar(centroModificar);
			return true;
		}
		return false;
	}

	public void eliminarCentroTrabajo(CentroEmpleador centroEliminar) throws NotRemovableException {
		if (centroEliminable(centroEliminar)) {
			centros.remove(centroEliminar);
			new CentroEmpleadorDAOImpl().eliminar(centroEliminar.getCodigo());
		} else {
			throw new NotRemovableException(
					"El centro de trabajo no puede ser eliminado ya que posee ofertas existentes.");
		}
	}

	public void cargarCandidatosDesdeBD() {
		candidatos = (ArrayList<Candidato>) new CandidatoDAOImpl().listarTodos();
	}

	public void registrarCandidato(Candidato nuevoCandidato) {
		candidatos.add(nuevoCandidato);
		new CandidatoDAOImpl().insertar(nuevoCandidato);
	}

	public void modificarCandidato(Candidato candidatoModificar) {
		new CandidatoDAOImpl().actualizar(candidatoModificar);
	}

	public void eliminarCandidato(Candidato candidatoEliminar) throws NotRemovableException {
		if (candidatoEliminable(candidatoEliminar)) {
			candidatos.remove(candidatoEliminar);
			new CandidatoDAOImpl().eliminar(candidatoEliminar.getCodigo());
		} else {
			throw new NotRemovableException(
					"El candidato no puede ser eliminado ya que esta vinculado con una solicitud.");
		}
	}

	public Candidato buscarCandidatoPorCodigo(String codigo) {
		for (Candidato c : candidatos) {
			if (c.getCodigo().equalsIgnoreCase(codigo)) {
				return c;
			}
		}
		return null;
	}

	public OfertaLaboral buscarOfertaPorCodigo(String codigo) {
		for (OfertaLaboral o : ofertas) {
			if (o.getCodigo().equalsIgnoreCase(codigo)) {
				return o;
			}
		}
		return null;
	}

	public Solicitud buscarSolicitudPorCodigo(String codigo) {
		for (Solicitud s : solicitudes) {
			if (s.getCodigo().equalsIgnoreCase(codigo)) {
				return s;
			}
		}
		return null;
	}

	public Candidato buscarCandidatoByCodigo(String codigo) {
		Candidato encontrado = null;
		int indice = 0;
		while (encontrado == null && indice < candidatos.size()) {
			if (candidatos.get(indice).getCodigo().equals(codigo)) {
				encontrado = candidatos.get(indice);
			}
			indice++;
		}
		return encontrado;
	}

	public CentroEmpleador buscarCentroByCodigo(String codigo) {
		CentroEmpleador encontrado = null;
		int indice = 0;
		while (encontrado == null && indice < centros.size()) {
			if (centros.get(indice).getCodigo().equals(codigo)) {
				encontrado = centros.get(indice);
			}
			indice++;
		}
		return encontrado;
	}

	public ArrayList<ResultadoMatcheo> obtenerCandidatosOrdenadosParaOferta(OfertaLaboral oferta) {
		ArrayList<ResultadoMatcheo> ordenados = new ArrayList<>();
		for (Candidato candidato : candidatos) {
			if (candidato.getEstado().equals("Desempleado")) {
				int puntaje = calcularPuntaje(candidato, oferta);
				if (puntaje >= oferta.getPorcentajeMinimo()) {
					String condicion = obtenerCondicion(puntaje, oferta.getPorcentajeMinimo());
					ordenados.add(new ResultadoMatcheo(oferta, candidato, puntaje, condicion));
				}
			}
		}
		ordenados.sort((a, b) -> b.getPorcentaje() - a.getPorcentaje());
		return ordenados;
	}

	private int calcularPuntaje(Candidato candidato, OfertaLaboral oferta) {
		int puntaje = 0;

		if (candidato.getModalidad().equalsIgnoreCase(oferta.getModalidad())) {
			puntaje += 10;
		}
		if (candidato.getJornada().equalsIgnoreCase(oferta.getJornada())) {
			puntaje += 10;
		}
		if (candidato.getAreaDeInteres().equalsIgnoreCase(oferta.getArea())) {
			puntaje += 20;
		}

		if (candidato.getIdProvincia() == oferta.getOfertador().getIdProvincia()) {
			puntaje += 10;
		} else if (candidato.isDisposicionMudarse() || oferta.isOfreceReubicacion()) {
			puntaje += 5;
		}

		if (candidato.getAspiracionSalarial() <= oferta.getSalario()) {
			puntaje += 10;
		} else {
			float exceso = candidato.getAspiracionSalarial() - oferta.getSalario();
			float porcentajeExceso = exceso / oferta.getSalario();
			if (porcentajeExceso <= 0.35f) {
				puntaje += Math.round(10 * (1 - porcentajeExceso / 0.35f));
			}
		}

		int idiomasRequeridos = oferta.getIdiomasRequeridas().size();
		int idiomasPuntos = 0;
		for (String idioma : oferta.getIdiomasRequeridas()) {
			if (candidato.getIdiomas().contains(idioma)) {
				idiomasPuntos++;
			}
		}
		puntaje += Math.min(10, (idiomasPuntos * 10) / Math.max(1, idiomasRequeridos));

		// ===== Matching por tipo (ids) =====
		if (candidato instanceof Universitario
				&& oferta.getNivelAcademico().equalsIgnoreCase("Estudiante Universitario")) {
			Universitario u = (Universitario) candidato;
			puntaje += 5;
			if (oferta.getIdCarrera() != null && u.getIdCarrera() == oferta.getIdCarrera()) {
				puntaje += 15;
			}

		} else if (candidato instanceof TecnicoSuperior
				&& oferta.getNivelAcademico().equalsIgnoreCase("Estudiante Técnico")) {
			TecnicoSuperior t = (TecnicoSuperior) candidato;
			puntaje += 5;
			if (oferta.getIdAreaTecnica() != null && t.getIdAreaTecnica() == oferta.getIdAreaTecnica()) {
				puntaje += 10;
			}
			if (t.getAniosExperiencia() >= oferta.getExperienciaMinima()) {
				puntaje += 5;
			}

		} else if (candidato instanceof Obrero
				&& oferta.getNivelAcademico().equalsIgnoreCase("Obrero")) {
			Obrero o = (Obrero) candidato;
			puntaje += 10;
			if (oferta.getIdHabilidad() != null) {
				String habReq = oferta.getNombreHabilidad();
				if (habReq != null && !habReq.isEmpty() && o.getHabilidades().contains(habReq)) {
					puntaje += 10;
				}
			}
		}

		if (oferta.isObligatorioLicencia()) {
			if (candidato.isLicenciaConducir()) {
				puntaje += 5;
			} else {
				puntaje -= 20;
			}
		} else if (candidato.isLicenciaConducir()) {
			puntaje += 2;
		}

		if (oferta.isObligatorioMayorDeEdad()) {
			if (candidato.getEdad() >= 18) {
				puntaje += 5;
			} else {
				puntaje -= 25;
			}
		} else {
			puntaje += 5;
		}

		return Math.max(0, puntaje);
	}

	public void eliminarOfertaTrabajo(OfertaLaboral seleccionado) throws NotRemovableException {
		if (ofertaEliminable(seleccionado)) {
			seleccionado.getOfertador().getOfertasLaborales().remove(seleccionado);
			ofertas.remove(seleccionado);
			new OfertaLaboralDAOImpl().eliminar(seleccionado.getCodigo());
		} else {
			throw new NotRemovableException(
					"La oferta no es eliminable ya que esta vinculada con una solicitud.");
		}
	}

	public void cargarSolicitudesDesdeBD() {
		List<Solicitud> listaBD = new SolicitudDAOImpl().listarTodos();
		solicitudes = new ArrayList<>();
		for (Solicitud sol : listaBD) {
			Candidato cand = buscarCandidatoPorCodigo(sol.getSolicitante().getCodigo());
			if (cand != null) {
				sol.setSolicitante(cand);
				if (!cand.getMisSolicitudes().contains(sol)) {
					cand.addSolicitud(sol);
				}
			}
			OfertaLaboral ofer = buscarOfertaPorCodigo(sol.getOfertaSolicitada().getCodigo());
			if (ofer != null) {
				sol.setOfertaSolicitada(ofer);
			}
			solicitudes.add(sol);
		}
	}

	public void cargarVacantesDesdeBD() {
		List<VacanteCompletada> listaBD = new VacanteCompletadaDAOImpl().listarTodos();
		vacantes = new ArrayList<>();
		for (VacanteCompletada vac : listaBD) {
			Solicitud sol = buscarSolicitudPorCodigo(vac.getSolicitudAceptada().getCodigo());
			OfertaLaboral ofer = buscarOfertaPorCodigo(vac.getOfertaOcupada().getCodigo());
			if (sol != null) vac.setSolicitudAceptada(sol);
			if (ofer != null) vac.setOfertaOcupada(ofer);
			vacantes.add(vac);
		}

	}

	public OfertaLaboral buscarOfertaByCodigo(String codigo) {
		OfertaLaboral encontrado = null;
		int indice = 0;
		while (encontrado == null && indice < ofertas.size()) {
			if (ofertas.get(indice).getCodigo().equals(codigo)) {
				encontrado = ofertas.get(indice);
			}
			indice++;
		}
		return encontrado;
	}

	public int buscarIndiceOfertaByCodigo(String codigo) {
		int indice = 0;
		boolean encontrado = false;
		while (!encontrado && indice < ofertas.size()) {
			if (ofertas.get(indice).getCodigo().equalsIgnoreCase(codigo)) {
				encontrado = true;
			} else {
				indice++;
			}
		}
		return encontrado ? indice : -1;
	}

	public void cargarOfertasDesdeBD() {
		ofertas = (ArrayList<OfertaLaboral>) new OfertaLaboralDAOImpl().listarTodos();
	}

	public void registrarOfertaLaboral(OfertaLaboral nuevaOferta) {
		ofertas.add(nuevaOferta);
		nuevaOferta.getOfertador().getOfertasLaborales().add(nuevaOferta);
		new OfertaLaboralDAOImpl().insertar(nuevaOferta);
	}

	public boolean modificarOfertaLaboral(OfertaLaboral ofertaModificar) {
		int indice = buscarIndiceOfertaByCodigo(ofertaModificar.getCodigo());
		if (indice != -1) {
			ofertas.set(indice, ofertaModificar);
			new OfertaLaboralDAOImpl().actualizar(ofertaModificar);
			return true;
		}
		return false;
	}

	public boolean ofertaVinculada(OfertaLaboral oferta) {
		for (Solicitud solicitud : solicitudes) {
			if (solicitud.getOfertaSolicitada().getCodigo().equals(oferta.getCodigo())) {
				return true;
			}
		}
		return false;
	}

	public void regVacanteCompletada(Solicitud solicitudContratada) {
		solicitudContratada.setEstado("Empleado");
		OfertaLaboral oferta = solicitudContratada.getOfertaSolicitada();
		oferta.setVacantes(oferta.getVacantes() - 1);
		if (oferta.getVacantes() <= 0) {
			oferta.setEstado("Inactiva");
		}
		new OfertaLaboralDAOImpl().actualizar(oferta);

		Candidato candidatoContratado = solicitudContratada.getSolicitante();
		candidatoContratado.cambiarEstadoSolicitudesAEmpleado();
		new CandidatoDAOImpl().actualizarEstado(candidatoContratado.getCodigo(), "Empleado");
		new SolicitudDAOImpl().actualizar(solicitudContratada);

		VacanteCompletada nuevaVacante = new VacanteCompletada(null, solicitudContratada, oferta, LocalDate.now());
		new VacanteCompletadaDAOImpl().insertar(nuevaVacante);
		vacantes.add(nuevaVacante);
	}

	public void regUsuario(Usuario user) {
		new UsuarioDAOImpl().insertar(user);
	}

	public Usuario login(String nombre, String clave) {
		Usuario user = new UsuarioDAOImpl().buscarPorNombre(nombre);
		if (user != null && user.match(nombre, clave)) {
			return user;
		}
		return null;
	}

	public boolean centroEliminable(CentroEmpleador centro) {
		return centro.getOfertasLaborales().size() == 0;
	}

	public boolean candidatoEliminable(Candidato candidato) {
		return candidato.getMisSolicitudes().size() == 0;
	}

	private boolean ofertaEliminable(OfertaLaboral seleccionado) {
		for (Solicitud sol : solicitudes) {
			if (sol.getOfertaSolicitada().equals(seleccionado)) {
				return false;
			}
		}
		return true;
	}

	public ArrayList<OfertaLaboral> ofertasDisponibles() {
		ArrayList<OfertaLaboral> disponibles = new ArrayList<>();
		for (OfertaLaboral ofr : ofertas) {
			if (ofr.getVacantes() > 0) {
				disponibles.add(ofr);
			}
		}
		return disponibles;
	}

	public ArrayList<ResultadoMatcheo> procesamientoAvanzando() {
		ArrayList<ResultadoMatcheo> resultados = new ArrayList<>();
		for (OfertaLaboral ofr : ofertas) {
			if (ofr.getVacantes() > 0) {
				resultados.addAll(obtenerCandidatosOrdenadosParaOferta(ofr));
			}
		}
		return resultados;
	}

	public String obtenerCondicion(int puntaje, int limitePuntaje) {
		double noRecomendadoMax = Math.max(Math.min(limitePuntaje * 1.3, 65), 50);
		double aceptableMax = Math.max(Math.min(limitePuntaje * 1.6, 85), 65);
		if (puntaje < noRecomendadoMax) {
			return "No recomendado";
		} else if (puntaje < aceptableMax) {
			return "Aceptable";
		}
		return "Recomendado";
	}

	public ResultadoMatcheo buscarResultado(ArrayList<ResultadoMatcheo> resultados,
	                                        String codigoOferta, String codigoCandidato) {
		for (ResultadoMatcheo r : resultados) {
			if (r.getOferta().getCodigo().equals(codigoOferta)
					&& r.getSolicitante().getCodigo().equals(codigoCandidato)) {
				return r;
			}
		}
		return null;
	}

	public boolean vincularOferta(ResultadoMatcheo resMatchSelec) {
		boolean aux = false;
		if (resMatchSelec.getOferta().getVacantes() > 0) {
			Solicitud sol = new Solicitud(null, LocalDate.now(), "Enviada",
					resMatchSelec.getSolicitante(), resMatchSelec.getOferta());
			if (verificarSolicitud(sol)) {
				solicitudes.add(sol);
				resMatchSelec.getSolicitante().addSolicitud(sol);
				resMatchSelec.getSolicitante().setEstado("En Espera");
				new SolicitudDAOImpl().insertar(sol);
				aux = true;
			}
		}
		return aux;
	}

	public boolean verificarSolicitud(Solicitud solicitud) {
		for (Solicitud sol : solicitudes) {
			if (matchSolicitud(sol, solicitud)) {
				return false;
			}
		}
		return true;
	}

	public boolean matchSolicitud(Solicitud s1, Solicitud s2) {
		return s1.getFechaSolicitud().equals(s2.getFechaSolicitud())
				&& s1.getEstado().equals(s2.getEstado())
				&& s1.getSolicitante().equals(s2.getSolicitante())
				&& s1.getOfertaSolicitada().equals(s2.getOfertaSolicitada());
	}

	public void contratarCandidato(Solicitud solicitud) {
		VacanteCompletada vacante = new VacanteCompletada(null,
				solicitud, solicitud.getOfertaSolicitada(), LocalDate.now());

		solicitud.setEstado("Aprobada");
		solicitud.getOfertaSolicitada().setVacantes(solicitud.getOfertaSolicitada().getVacantes() - 1);
		solicitud.getSolicitante().setEstado("Empleado");
		solicitud.getSolicitante().cambiarEstadoSolicitudesAEmpleado();

		new CandidatoDAOImpl().actualizarEstado(solicitud.getSolicitante().getCodigo(), "Empleado");
		if (solicitud.getOfertaSolicitada().getVacantes() == 0) {
			solicitud.getOfertaSolicitada().setEstado("Completada");
		}
		new OfertaLaboralDAOImpl().actualizar(solicitud.getOfertaSolicitada());
		new SolicitudDAOImpl().actualizar(solicitud);

		new VacanteCompletadaDAOImpl().insertar(vacante);
		vacantes.add(vacante);
	}

	public void rechazarCandidato(Solicitud solicitud) {
		solicitud.setEstado("Rechazada");
		solicitud.getSolicitante().setEstado("Desempleado");
		solicitud.getSolicitante().cambiarEstadoSolicitudesADesempleado();
		new CandidatoDAOImpl().actualizarEstado(solicitud.getSolicitante().getCodigo(), "Desempleado");
		new SolicitudDAOImpl().actualizar(solicitud);
	}

	public Solicitud buscarSolicitudByCodigo(String codigo) {
		for (Solicitud s : solicitudes) {
			if (s.getCodigo().equals(codigo)) {
				return s;
			}
		}
		return null;
	}

	public boolean esProcesable(Solicitud seleccionado) {
		return !(seleccionado.getEstado().equals("Rechazada")
				|| seleccionado.getEstado().equals("Aprobada"));
	}

	public ArrayList<Solicitud> obtenerSolicitudesVinculadas(OfertaLaboral oferta) {
		ArrayList<Solicitud> solicitudesV = new ArrayList<>();
		for (Solicitud sol : solicitudes) {
			if (sol.getOfertaSolicitada().getCodigo().equals(oferta.getCodigo())) {
				solicitudesV.add(sol);
			}
		}
		return solicitudesV;
	}

	public int calcularTasaCovertura() {
		int cantVacantes = 0;
		for (OfertaLaboral ofr : ofertas) {
			cantVacantes += ofr.getVacantes();
		}
		if (ofertas.size() > 0) {
			return Math.round(((float) cantVacantes / (float) ofertas.size()) * 100);
		}
		return 0;
	}

	public int obtenerOfertasVacias() {
		int cantidad = 0;
		for (OfertaLaboral ofr : ofertas) {
			boolean encontrado = false;
			for (Solicitud sol : solicitudes) {
				if (sol.getOfertaSolicitada().equals(ofr)) {
					encontrado = true;
					break;
				}
			}
			if (!encontrado) {
				cantidad++;
			}
		}
		return cantidad;
	}
}