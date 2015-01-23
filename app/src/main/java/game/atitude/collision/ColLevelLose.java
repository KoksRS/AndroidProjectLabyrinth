package game.atitude.collision;

import android.util.Log;

import com.artem_tereshko.androidproject.GameListener;

import game.AbstractGameObject;
import game.object.circle.Ball;
import mesmaths.geometrie.base.Vecteur;

public class ColLevelLose extends Collision {

    private GameListener _gameListener;


    public ColLevelLose(AbstractGameObject abstractGameObject, GameListener gameListener) {
        super(abstractGameObject);
        _gameListener = gameListener;
    }



    @Override
    public void collisionGameObject(AbstractGameObject abstractGameObject) {
        this._GameObjectDecorated.collisionGameObject(abstractGameObject);

        if( this.isIntersectBy(abstractGameObject) && abstractGameObject.getAbstractGameObject() instanceof Ball)
            _gameListener.onGameLose();
    }



    @Override//Collision avec le bord
    public void collisionContour(double abscisseCoinHautGauche, double ordonnéeCoinHautGauche, double largeur, double hauteur)
    {
        this._GameObjectDecorated.collisionContour(abscisseCoinHautGauche, ordonnéeCoinHautGauche, largeur, hauteur);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */

    @Override
    public String toString()
    {
        return "Collision Lose, "+ this._GameObjectDecorated.toString();
    }

}