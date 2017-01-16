import {UserDto} from '../dto/User.DTO';

const Entities: {[name: string]: {new (): any}} = {
    UserDto : UserDto
};

export { Entities }
