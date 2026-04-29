import {OwnerType} from '../../../enums/owner-type.enum';

export interface MediaRequest {
  file: File;
  folder: string;
  products: string;
  ownerType: OwnerType;
  orderIndex?: number;
}
