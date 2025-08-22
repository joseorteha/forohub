export interface User {
  id: number;
  nombre: string;
  email: string;
}

export interface AuthResponse {
  datosJwtToken: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  nombre: string;
  email: string;
  password: string;
}

export interface Topico {
  id: number;
  titulo: string;
  mensaje: string;
  fechaCreacion: string;
  status: TopicoStatus;
  autor: User;
  curso: Curso;
  respuestas?: Respuesta[];
}

export interface TopicoRequest {
  titulo: string;
  mensaje: string;
  autorId: number;
  cursoId: number;
}

export interface TopicoUpdateRequest {
  titulo?: string;
  mensaje?: string;
}

export interface Respuesta {
  id: number;
  mensaje: string;
  fechaCreacion: string;
  autor: User;
  topico?: Topico;
  solucion: boolean;
}

export interface RespuestaRequest {
  mensaje: string;
  autorId: number;
  topicoId: number;
}

export interface Curso {
  id: number;
  nombre: string;
  categoria: string;
}

export type TopicoStatus = 'ABIERTO' | 'CERRADO' | 'RESUELTO';

export interface ApiError {
  message: string;
  status: number;
}

export interface PaginatedResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}
