import { BaseEntity } from './../../shared';

export class Merchant implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public phone?: string,
        public address?: string,
        public login?: string,
        public password?: string,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public imageUrl?: string,
        public photoContentType?: string,
        public photo?: any,
        public about?: string,
        public ableToTravel?: string,
        public location?: string,
    ) {
    }
}
