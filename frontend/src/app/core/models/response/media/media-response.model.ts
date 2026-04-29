import {OwnerType} from '../../../enums/owner-type.enum';

export interface MediaResponse {
  name: string;
  publicId: string;
  url: string;
  folder: string;
  ownerType: OwnerType;
  orderIndex: number;
}
