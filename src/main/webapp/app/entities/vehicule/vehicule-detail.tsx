import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './vehicule.reducer';
import { IVehicule } from 'app/shared/model/vehicule.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IVehiculeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class VehiculeDetail extends React.Component<IVehiculeDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { vehiculeEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="autocarsApp.vehicule.detail.title">Vehicule</Translate> [<b>{vehiculeEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="codeIntern">
                <Translate contentKey="autocarsApp.vehicule.codeIntern">Code Intern</Translate>
              </span>
            </dt>
            <dd>{vehiculeEntity.codeIntern}</dd>
            <dt>
              <span id="immatriculation">
                <Translate contentKey="autocarsApp.vehicule.immatriculation">Immatriculation</Translate>
              </span>
            </dt>
            <dd>{vehiculeEntity.immatriculation}</dd>
            <dt>
              <span id="dateDeMiseEnCirculation">
                <Translate contentKey="autocarsApp.vehicule.dateDeMiseEnCirculation">Date De Mise En Circulation</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={vehiculeEntity.dateDeMiseEnCirculation} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
          </dl>
          <Button tag={Link} to="/entity/vehicule" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/vehicule/${vehiculeEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ vehicule }: IRootState) => ({
  vehiculeEntity: vehicule.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(VehiculeDetail);
