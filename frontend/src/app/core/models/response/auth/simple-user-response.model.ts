export interface SimpleUserResponse {
  id: number;
  username: string;
  email: string;
  image?: string;
  tenantId?: string;
  roles: string[];
}
