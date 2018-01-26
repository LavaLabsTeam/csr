import { User } from './../../shared/models/user';
import { UserMerchant } from './user-merchant';
export interface UserMerchantResource {
    id: number;
    merchant: UserMerchant;
    user: User
}
