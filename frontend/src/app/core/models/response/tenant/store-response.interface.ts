import { Address } from "../../address.model";
import { BranchResponse } from "./branch-response.interface";

export interface StoreResponse {
    id?: string;
    name?: string;
    active?: boolean;
    currency?: string;
    defaultLanguage?: string;
    timezone?: string;
    ownerName?: string;
    ownerInfo?: string;
    info?: string;
    address?: Address;
    branches?: BranchResponse[];
    tenantId?: string;
    createdAt?: string;
    updatedAt?: string;
}