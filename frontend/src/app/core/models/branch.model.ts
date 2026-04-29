import { Address } from "./address.model"

export interface Branch {
    id?: string;
    storeId?: string;
    name?: string;
    active?: boolean;
    address?: Address;
    managerName?: string;
    managerPhone?: string;
    openingHours?: string;
    closingHours?: string;
    tenantId?: string;
    createdAt?: string;
    updatedAt?: string;
}