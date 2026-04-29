import { Address } from "../../address.model";

export interface BranchRequest {
    name?: string;
    managerName?: string;
    managerPhone?: string;
    openingHours?: string;
    closingHours?: string;
    address?: Address;
}