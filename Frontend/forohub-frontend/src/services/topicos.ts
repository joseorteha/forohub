import { apiService } from './api';
import type { Topico, TopicoRequest, TopicoUpdateRequest, PaginatedResponse } from '../types';

export const topicoService = {
  async getTopicos(page: number = 0, size: number = 10): Promise<PaginatedResponse<Topico>> {
    return apiService.get<PaginatedResponse<Topico>>(`/topicos?page=${page}&size=${size}&sort=fechaCreacion,desc`);
  },

  async getTopicoById(id: number): Promise<Topico> {
    return apiService.get<Topico>(`/topicos/${id}`);
  },

  async createTopico(topico: TopicoRequest): Promise<Topico> {
    return apiService.post<Topico>('/topicos', topico);
  },

  async updateTopico(id: number, topico: TopicoUpdateRequest): Promise<Topico> {
    return apiService.put<Topico>(`/topicos/${id}`, topico);
  },

  async deleteTopico(id: number): Promise<void> {
    return apiService.delete<void>(`/topicos/${id}`);
  },

  async searchTopicos(query: string, page: number = 0, size: number = 10): Promise<PaginatedResponse<Topico>> {
    const encodedQuery = encodeURIComponent(query);
    return apiService.get<PaginatedResponse<Topico>>(`/topicos/search?q=${encodedQuery}&page=${page}&size=${size}`);
  }
};
