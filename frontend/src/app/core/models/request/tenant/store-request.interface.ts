import { Address } from "../../address.model";

export interface StoreRequest {
    id?: string;
    name?: string;
    currency?: string;
    defaultLanguage?: string;
    timezone?: string;
    ownerName?: string;
    ownerInfo?: string;
    info?: string;
    address?: Address;
}