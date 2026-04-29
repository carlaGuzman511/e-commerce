import { Address } from "../../address.model";

export interface BranchResponse {
    id?: string;
    storeId?: string;
    name?: boolean;
    active?: string;
    address?: Address;
    managerName?: string;
    managerPhone?: string;
    openingHours?: string;
    closingHours?: string;
    tenantId?: string;
    createdAt?: string;
    updatedAt?: string;
}