import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IChauffeur } from 'app/shared/model/chauffeur.model';
import { getEntities as getChauffeurs } from 'app/entities/chauffeur/chauffeur.reducer';
import { getEntity, updateEntity, createEntity, reset } from './vehicule.reducer';
import { IVehicule } from 'app/shared/model/vehicule.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IVehiculeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IVehiculeUpdateState {
  isNew: boolean;
  chauffeurId: string;
}

export class VehiculeUpdate extends React.Component<IVehiculeUpdateProps, IVehiculeUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      chauffeurId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getChauffeurs();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { vehiculeEntity } = this.props;
      const entity = {
        ...vehiculeEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/vehicule');
  };

  render() {
    const { vehiculeEntity, chauffeurs, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="autocarsApp.vehicule.home.createOrEditLabel">
              <Translate contentKey="autocarsApp.vehicule.home.createOrEditLabel">Create or edit a Vehicule</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : vehiculeEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="vehicule-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="codeInternLabel" for="codeIntern">
                    <Translate contentKey="autocarsApp.vehicule.codeIntern">Code Intern</Translate>
                  </Label>
                  <AvField
                    id="vehicule-codeIntern"
                    type="text"
                    name="codeIntern"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="immatriculationLabel" for="immatriculation">
                    <Translate contentKey="autocarsApp.vehicule.immatriculation">Immatriculation</Translate>
                  </Label>
                  <AvField
                    id="vehicule-immatriculation"
                    type="text"
                    name="immatriculation"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="dateDeMiseEnCirculationLabel" for="dateDeMiseEnCirculation">
                    <Translate contentKey="autocarsApp.vehicule.dateDeMiseEnCirculation">Date De Mise En Circulation</Translate>
                  </Label>
                  <AvField
                    id="vehicule-dateDeMiseEnCirculation"
                    type="date"
                    className="form-control"
                    name="dateDeMiseEnCirculation"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/vehicule" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  chauffeurs: storeState.chauffeur.entities,
  vehiculeEntity: storeState.vehicule.entity,
  loading: storeState.vehicule.loading,
  updating: storeState.vehicule.updating,
  updateSuccess: storeState.vehicule.updateSuccess
});

const mapDispatchToProps = {
  getChauffeurs,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(VehiculeUpdate);
