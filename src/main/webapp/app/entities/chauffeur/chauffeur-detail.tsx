import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './chauffeur.reducer';
import { IChauffeur } from 'app/shared/model/chauffeur.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IChauffeurDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ChauffeurDetail extends React.Component<IChauffeurDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { chauffeurEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="autocarsApp.chauffeur.detail.title">Chauffeur</Translate> [<b>{chauffeurEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="nom">
                <Translate contentKey="autocarsApp.chauffeur.nom">Nom</Translate>
              </span>
            </dt>
            <dd>{chauffeurEntity.nom}</dd>
            <dt>
              <span id="prenom">
                <Translate contentKey="autocarsApp.chauffeur.prenom">Prenom</Translate>
              </span>
            </dt>
            <dd>{chauffeurEntity.prenom}</dd>
            <dt>
              <span id="telephone">
                <Translate contentKey="autocarsApp.chauffeur.telephone">Telephone</Translate>
              </span>
            </dt>
            <dd>{chauffeurEntity.telephone}</dd>
            <dt>
              <Translate contentKey="autocarsApp.chauffeur.vehicule">Vehicule</Translate>
            </dt>
            <dd>
              {chauffeurEntity.vehicules
                ? chauffeurEntity.vehicules.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.id}</a>
                      {i === chauffeurEntity.vehicules.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
          </dl>
          <Button tag={Link} to="/entity/chauffeur" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/chauffeur/${chauffeurEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ chauffeur }: IRootState) => ({
  chauffeurEntity: chauffeur.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ChauffeurDetail);
