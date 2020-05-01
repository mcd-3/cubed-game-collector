package com.matthew.carvalhodagenais.gamecubecollector.viewmodels

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.matthew.carvalhodagenais.gamecubecollector.R
import com.matthew.carvalhodagenais.gamecubecollector.data.repositories.GameRepository
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Game
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Type
import com.matthew.carvalhodagenais.gamecubecollector.data.repositories.ConditionRepository
import com.matthew.carvalhodagenais.gamecubecollector.data.repositories.RegionRepository
import com.matthew.carvalhodagenais.gamecubecollector.helpers.DateHelper
import com.matthew.carvalhodagenais.gamecubecollector.helpers.ImageStorageHelper
import com.matthew.carvalhodagenais.gamecubecollector.helpers.StringHelper
import com.matthew.carvalhodagenais.gamecubecollector.ui.GameAddEditFragment
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.*

class GameAddEditViewModel(application: Application): AndroidViewModel(application) {

    private var selectedGame = MutableLiveData<Game>()
    private var gameRepository = GameRepository(application)
    private var regionRepository = RegionRepository(application)
    private var conditionRepository = ConditionRepository(application)

    fun insert(game: Game) = viewModelScope.launch {
        gameRepository.insertGame(game)
        clearCurrentlySelectedGame()
    }

    fun update(game: Game) = viewModelScope.launch {
        if (selectedGame.value?.imageName != "") {
            ImageStorageHelper.deleteImage(
                ImageStorageHelper.IMAGE_PATH, selectedGame.value?.imageName!!)
        }
        gameRepository.updateGame(game)
        clearCurrentlySelectedGame()
    }

    fun setSelectedGame(game: Game) {
        selectedGame.value = game
    }

    fun getSelectedGame(): Game? {
        return selectedGame.value
    }

    fun clearCurrentlySelectedGame() {
        selectedGame = MutableLiveData<Game>()
    }

    fun getRegionRepository(): RegionRepository = regionRepository

    fun getConditionRepository(): ConditionRepository = conditionRepository

    fun getConditionTypeID(): Int = Type.CD_ID

    /**
     * Saves a game to the database
     *
     * @param requestInt Int
     * @param title String
     * @param publisher String
     * @param developer String
     * @param releaseDate Date?
     * @param pricePaid Double?
     * @param boughtDate Date?
     * @param hasCase Boolean
     * @param hasManual Boolean
     * @param isFavouriteTag String
     * @param regionCode String
     * @param conditionCode String
     * @param bitmap Bitmap
     */
    fun saveGame(
        requestInt: Int,
        title: String,
        publisher: String,
        developer: String,
        releaseDate: Date?,
        pricePaid: Double?,
        boughtDate: Date?,
        hasCase: Boolean,
        hasManual: Boolean,
        isFavouriteTag: String,
        regionCode: String,
        conditionCode: String,
        bitmap: Bitmap
    ) = viewModelScope.launch {
        // Get the region ID
        var regionId: Int? = null
        val getRegionIdOperation = async {
            val region = regionRepository.getRegionByCode(regionCode)
            regionId = region.id
        }
        getRegionIdOperation.await()

        var conditionId: Int? = null
        val getConditionIdOperation = async {
            val condition = conditionRepository.getConditionByCodeAndType(conditionCode, Type.CD_ID)
            conditionId = condition.id
        }
        getConditionIdOperation.await()

        val name = ImageStorageHelper.generateUniqueImageName()
        ImageStorageHelper.save(
            getApplication<Application>().applicationContext,
            bitmap,
            name
        )

        val game = Game(
            title =  title,
            publishers = StringHelper.setNullIfEmptyString(publisher),
            developers = StringHelper.setNullIfEmptyString(developer),
            releaseDate = releaseDate,
            pricePaid = pricePaid,
            boughtDate = boughtDate,
            hasCase = hasCase,
            hasManual = hasManual,
            regionId = regionId,
            conditionId = conditionId
        )
        game.isFavourite =
            (isFavouriteTag == getApplication<Application>().resources.getString(R.string.star_filled_tag))
        game.imageName = name

        if (requestInt ==  GameAddEditFragment.EDIT_REQUEST) { // Edit the game
            game.id = selectedGame.value!!.id
            update(game)
        } else { // Add the game
            insert(game)
        }
    }

    /**
     * Creates a string from a Date or returns the default "No Date Set" text if date is null
     *
     * @param date
     * @return String
     */
    fun getDateString(date: Date?): String? =
        DateHelper.createDateString(date)

    /**
     * Creates a tag for the favourite image
     */
    fun createFavouriteButtonTag(): String {
        return (
                if (selectedGame.value != null && selectedGame.value?.isFavourite!!)
                    getApplication<Application>().resources.getString(R.string.star_filled_tag)
                else
                    getApplication<Application>().resources.getString(R.string.star_border_tag))
    }
}