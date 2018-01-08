import { BaseEntity, User } from './../../shared';

export class UserMerchant implements BaseEntity {
    constructor(
        public id?: number,
        public user?: User,
        public merchant?: BaseEntity,
    ) {
    }
}
