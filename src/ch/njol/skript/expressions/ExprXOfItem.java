/*
 *   This file is part of Skript.
 *
 *  Skript is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Skript is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Skript.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * 
 * Copyright 2011, 2012 Peter Güttinger
 * 
 */

package ch.njol.skript.expressions;

import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SimpleExpression;
import ch.njol.skript.lang.SkriptParser.ParseResult;

/**
 * @author Peter Güttinger
 * 
 */
public class ExprXOfItem extends SimpleExpression<ItemStack> {
	
	static {
		Skript.registerExpression(ExprXOfItem.class, ItemStack.class, "<\\d+> of %itemstacks%");
	}
	
	private int amount;
	private Expression<ItemStack> items;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(final Expression<?>[] exprs, final int matchedPattern, final ParseResult parseResult) {
		amount = Integer.parseInt(parseResult.regexes.get(0).group());
		items = (Expression<ItemStack>) exprs[0];
		return true;
	}
	
	@Override
	public boolean isSingle() {
		return items.isSingle();
	}
	
	@Override
	public Class<? extends ItemStack> getReturnType() {
		return ItemStack.class;
	}
	
	@Override
	protected ItemStack[] getAll(final Event e) {
		final ItemStack[] iss = items.getArray(e);
		for (final ItemStack is : iss)
			is.setAmount(amount);
		return iss;
	}
	
	@Override
	public String getDebugMessage(final Event e) {
		return amount + " of " + items.getDebugMessage(e);
	}
	
	@Override
	public String toString() {
		return amount + " of " + items;
	}
	
}
