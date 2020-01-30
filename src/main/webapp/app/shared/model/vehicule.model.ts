import { Moment } from 'moment';
import { IChauffeur } from 'app/shared/model/chauffeur.model';

export interface IVehicule {
  id?: number;
  codeIntern?: string;
  immatriculation?: string;
  dateDeMiseEnCirculation?: Moment;
  chauffeurs?: IChauffeur[];
}

export const defaultValue: Readonly<IVehicule> = {};
